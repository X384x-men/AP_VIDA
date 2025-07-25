import { Component, OnInit, ViewChild, Input, OnChanges, Output, EventEmitter } from '@angular/core';
import { MatMenuTrigger } from '@angular/material/menu';
import { DropDownMenu, SubMenu, Menu, SelectedMenuOption } from 'src/app/core/interface/menu/dropdown-menu';

@Component({
  selector: 'app-menu-component',
  templateUrl: './menu-component.component.html',
  styleUrls: ['./menu-component.component.css']
})
export class MenuComponentComponent implements OnInit, OnChanges {
  @Input() positions: number[];
  @ViewChild(MatMenuTrigger) contextMenu: MatMenuTrigger;
  @Output() selectedOption = new EventEmitter<SelectedMenuOption>();
  @Input() menuDropDown: DropDownMenu;
  private menu: Menu;
  contextMenuPosition = { x: '0px', y: '0px' };
  constructor() { }

  ngOnInit() {
  }
  onSelectOption(subMenu: SubMenu) {
    const selected: SelectedMenuOption = {
      index: this.menu.index,
      menuName: this.menu.name,
      subMenu: {
        index: subMenu.index,
        option: subMenu.option,
        enabled: subMenu.enabled
      }
    };
    this.selectedOption.emit(selected);
  }
  onSelectMenu(menu: Menu) {
    this.menu = menu;
  }
  ngOnChanges() {
    if (this.positions !== undefined && this.menuDropDown !== undefined) {
      this.contextMenuPosition.x = this.positions[0] + 'px';
      this.contextMenuPosition.y = this.positions[1] + 'px';
      this.contextMenu.openMenu();
    }
  }
}
