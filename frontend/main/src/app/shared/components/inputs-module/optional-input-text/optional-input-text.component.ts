import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { InputTextProps } from 'src/app/core/interface/input-options/input';

@Component({
  selector: 'app-optional-input-text',
  templateUrl: './optional-input-text.component.html',
  styleUrls: ['./optional-input-text.component.css']
})
export class OptionalInputTextComponent implements OnInit {
  @Output() value = new EventEmitter<string>();
  @Input() val: string;
  @Input() disabled = false;
  @Input() style: any;
  @Input() readonly = false;
  @Input() data: InputTextProps = {
    hint: '',
    placeholder: '',
    type: 'text'
  };
  constructor() { }

  ngOnInit() {
    if (this.val === undefined || this.val === null) {
      this.val = '';
    }
  }
  sendVal(event) {
    this.value.emit(event.target.value);
  }
}
