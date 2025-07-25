export interface DropDownMenu {
    menu: Array<Menu>;
}

export interface Menu {
    index: number;
    enabled: boolean;
    name: string;
    icon: string;
    options?: Array<SubMenu>;
}
export interface SubMenu {
    index: number;
    option: string;
    enabled: boolean;
    icon?: string;
}
export interface SelectedMenu {
    menu: SelectedMenuOption;
}
export interface SelectedMenuOption {
    index: number;
    menuName: string;
    subMenu: SubMenu;

}
