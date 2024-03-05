package com.example.siki.model;

public class Store {
	private Long Id;
	private String Name;
	private User user;
	private String Description;
	private String Avatar;
	private String BackgroundImage;
	private boolean Status;

    public Store() {
    }

    public Store(Long id, String name, User user, String description, String avatar, String backgroundImage, boolean status) {
        Id = id;
        Name = name;
        this.user = user;
        Description = description;
        Avatar = avatar;
        BackgroundImage = backgroundImage;
        Status = status;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getBackgroundImage() {
        return BackgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        BackgroundImage = backgroundImage;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }
}
