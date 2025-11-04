package com.darkyellowcat.weconnected.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdateRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private Long id;

    private String username;

    private String avatarUrl;

    private int gender;
}
