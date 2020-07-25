package com.amazon.review.controller;

import com.amazon.review.dto.ParsedLineDto;
import com.amazon.review.mapper.ProductMapper;
import com.amazon.review.mapper.ReviewMapper;
import com.amazon.review.mapper.UserMapper;
import com.amazon.review.model.Product;
import com.amazon.review.model.Review;
import com.amazon.review.model.Role;
import com.amazon.review.model.User;
import com.amazon.review.model.UserAws;
import com.amazon.review.service.ProductService;
import com.amazon.review.service.ReviewService;
import com.amazon.review.service.RoleService;
import com.amazon.review.service.UserAwsService;
import com.amazon.review.service.UserService;
import com.amazon.review.util.FileUtil;
import com.amazon.review.util.ParserCsv;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@AllArgsConstructor
public class InjectDataController {
    private static final String CSV_PATH = "/reviews.csv";
    private final ProductService productService;
    private final ReviewService reviewService;
    private final UserAwsService userAwsService;
    private final RoleService roleService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final ReviewMapper reviewMapper;
    private final ProductMapper productMapper;
    private final FileUtil fileUtil;
    private final ParserCsv parserCsv;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        injectTestData();
        injectRoles();
        injectUsers();
    }

    private void injectUsers() {
        User admin = new User();
        admin.setLogin("admin");
        admin.setRoles(Set.of(roleService.findByRoleName(Role.RoleName.ADMIN)));
        admin.setPassword(passwordEncoder.encode("1234"));
        User user = new User();
        user.setLogin("user");
        user.setRoles(Set.of(roleService.findByRoleName(Role.RoleName.USER)));
        user.setPassword(passwordEncoder.encode("1234"));
        userService.saveAll(Set.of(admin, user));
    }

    private void injectRoles() {
        Role user = new Role(Role.RoleName.USER);
        Role admin = new Role(Role.RoleName.ADMIN);
        roleService.saveAll(Set.of(user, admin));
    }

    private void injectTestData() {
        List<String> lines = fileUtil.readFromFile(CSV_PATH);
        List<ParsedLineDto> parsedLines = parserCsv.parseCsv(lines);
        Set<UserAws> users = new HashSet<>();
        Set<Review> reviews = new HashSet<>();
        Set<Product> products = new HashSet<>();
        for (ParsedLineDto parsedLineDto: parsedLines) {
            UserAws userAws = userMapper.getUserAwsFromParsedLineDto(parsedLineDto);
            users.add(userAws);
            Product product = productMapper.convertToProductFromParsedLineDto(parsedLineDto);
            products.add(product);
            Review review = reviewMapper
                    .getReviewFromParsedLineDto(parsedLineDto, userAws, product);
            reviews.add(review);
        }
        addEntitiesToDb(users, reviews, products);
    }

    private void addEntitiesToDb(Set<UserAws> users, Set<Review> reviews, Set<Product> products) {
        userAwsService.saveAll(users);
        log.info("Users have been uploaded!");
        productService.saveAll(products);
        log.info("Products have been uploaded!");
        reviewService.saveAll(reviews);
        log.info("Reviews have been uploaded!");
    }
}
