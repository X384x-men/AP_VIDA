export interface MatPanelMenu {
  menu: Array<Menu>;
}
export interface Menu {
  index: number;
  menuTitle: string;
  enabled: number;
  icon: string;
  url: string;
  params?: any;
  profile: Array<string>;
  subMenu?: Array<Options>;
}
export interface Options {
  index: number;
  name: string;
  profile: Array<string>;
  enabled: number;
  icon: string;
  url: string;
  params?: any;
}
