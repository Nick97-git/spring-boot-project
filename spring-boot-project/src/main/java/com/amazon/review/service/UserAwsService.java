package com.amazon.review.service;

import com.amazon.review.model.UserAws;
import java.util.List;

public interface UserAwsService extends GenericService<UserAws> {

    List<UserAws> getMostActiveUsers(int limit, int offset);
}
