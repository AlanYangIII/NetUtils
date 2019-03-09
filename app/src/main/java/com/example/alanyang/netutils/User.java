package com.example.alanyang.netutils;

public class User {
    private String url;
    private String params;//参数
    private String method;//方法
    private String property;

    public User() {
    }

    //从内部类的数据中
    public User(User origin) {
        this.url = origin.url;
        this.params = origin.params;
        this.method = origin.method;
        this.property = origin.property;
    }

    public String getUrl() {
        return url;
    }

    public String getParams() {
        return params;
    }

    public String getMethod() {
        return method;
    }

    public String getProperty() {
        return property;
    }

    //通过Request.Builder这个内部类来创建Request的对象
    public static class Builder {
        private User target;

        public Builder() {
            target = new User();
        }

        public Builder url(String url) {
            target.url = url;
            return this;//返回的是当前这个Request的对象target
        }

        public Builder params(String params) {
            target.params = params;
            return this;
        }

        public Builder method(String method) {
            target.method = method;
            return this;
        }

        public Builder property(String property) {
            target.property = property;
            return this;
        }

        public User build() {
            return new User(target);
        }
    }
}
