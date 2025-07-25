import { Component, OnInit, Input, OnChanges, Output, EventEmitter, ViewChild, ɵConsole, ViewEncapsulation, SimpleChange, SimpleChanges } from '@angular/core';
import { UntypedFormControl, Validators } from '@angular/forms';
import { SelectMenu } from 'src/app/core/interface/menu/select-menu';
import { IfStmt } from '@angular/compiler';
import { CdkVirtualScrollViewport } from '@angular/cdk/scrolling';

const SEGMENTO_DEFAULT: SelectMenu[] = [
  {
    index: 0,
    name: 'Residencial',
    extras: {
      name: 'Residencial',
      value: 10
    }
  }, {
    index: 1,
    name: 'Empresarial',
    extras: {
      name: 'Empresarial',
      value: 11
    }
  }
];

const LLAMADA_RESPONSABLE: SelectMenu[] = [
  {
    index: 0,
    name: 'Roberto Chavez',
    extras: {
      name: 'Roberto Chavez',
      value: 12
    }
  }
];

const DEFAULT_OPTION = 'D';
const SEGMENTO_OPTION = 'S';
const RESPONSABLE_OPTION = 'R';
@Component({
  selector: 'app-select-component',
  templateUrl: './select-component.component.html',
  styleUrls: ['./select-component.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class SelectComponentComponent implements OnInit, OnChanges {
  optionsControl = new UntypedFormControl({ disabled: true }, [Validators.required]);
  @Input() menuName = '';
  @Output() selectedOption = new EventEmitter<SelectMenu>();
  @Input() options: SelectMenu[];
  @Input() currentOption: SelectMenu;
  @Input() disabled = false;
  @Input() typeOption = DEFAULT_OPTION;
  @Input() width = '230px';
  @Input() search = false;
  @Input() valueSearch = '';

  optionsAux: SelectMenu[];

  constructor() {
  }

  ngOnInit() {
    if (this.disabled) {
      this.optionsControl.disable();
    }
    if (this.typeOption === SEGMENTO_OPTION) {
      this.options = SEGMENTO_DEFAULT;
    }

    if(this.typeOption === RESPONSABLE_OPTION){
      this.options === LLAMADA_RESPONSABLE;
    }
  }
  ngOnChanges(changes: SimpleChanges) {
    this.optionsControl.setValue(this.currentOption);
    if(changes.options){
      this.optionsAux = Object.assign([], this.options)
    }
    if(changes.disabled){
      if (this.disabled) {
        this.optionsControl.disable();
      }else{
        this.optionsControl.enable();
      }
    }
  }
  selectOption(option: SelectMenu, index: number) {
    option.index = index;
    this.selectedOption.emit(option);
  }
  compareObjects(o1: SelectMenu, o2: SelectMenu): boolean {
    if (o2 && o2 !== null && o2.index < 0) {
      return o2 !== undefined && o2 !== null && (o1.name === o2.name);
    }
    return o2 !== undefined && o2 !== null && (o1.name === o2.name && o1.index === o2.index);
  }

  onKey(value){
    console.log(value);
    if (value === '' || value === null) {
      this.options = Object.assign([], this.optionsAux);
    } else {
      this.options = Object.assign([], this.optionsAux);
      this.options = this.options.filter((item) => {
        return ( item.extras[this.valueSearch].toLowerCase().indexOf(value.toLowerCase()) ) !== -1;
      })
    }
  }


}
