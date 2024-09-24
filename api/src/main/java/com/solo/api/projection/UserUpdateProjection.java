package com.solo.api.projection;

import java.sql.Date;

public interface UserUpdateProjection {
    String getNickname();
    String getEmail();
    String getPhone();
    Date getBirthday();
    String getGender();
    Float getHeight();
    Float getWeight();
}
