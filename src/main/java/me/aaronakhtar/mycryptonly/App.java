package me.aaronakhtar.mycryptonly;

public enum App {

    version("1.0.0.0-STABLE"),
    name("MyCryptOnly");

    private String val;

    App(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
