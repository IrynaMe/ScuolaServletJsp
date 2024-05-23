package main.menu;

public class OpzioneMenu {
    String menuOption;
    String menuHref;
    String dropdownSection;

    public OpzioneMenu(String menuOption, String menuHref, String dropdownSection) {
        this.menuOption = menuOption;
        this.menuHref = menuHref;
        this.dropdownSection = dropdownSection;
    }

    public String getMenuOption() {
        return menuOption;
    }

    public void setMenuOption(String menuOption) {
        this.menuOption = menuOption;
    }

    public String getMenuHref() {
        return menuHref;
    }

    public void setMenuHref(String menuHref) {
        this.menuHref = menuHref;
    }

    public String getDropdownSection() {
        return dropdownSection;
    }

    public void setDropdownSection(String dropdownSection) {
        this.dropdownSection = dropdownSection;
    }
}
