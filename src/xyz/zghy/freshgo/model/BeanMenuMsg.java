package xyz.zghy.freshgo.model;

/**
 * @author ghy
 * @date 2020/7/3 ����8:31
 */
public class BeanMenuMsg {
    private int menuId;
    private String menuName;
    private String menuDesc;
    private String menuStep;
    //TODO ����ȱ��ͼƬ����

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public String getMenuStep() {
        return menuStep;
    }

    public void setMenuStep(String menuStep) {
        this.menuStep = menuStep;
    }

}
