import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { InputTextProps } from 'src/app/core/interface/input-options/input';

@Component({
  selector: 'app-required-optional-input-text',
  templateUrl: './required-optional-input-text.component.html',
  styleUrls: ['./required-optional-input-text.component.css']
})
export class RequiredOptionalInputTextComponent implements OnInit {
  @Output() value = new EventEmitter<string>();
  @Input() requiredInput: boolean;
  @Input() val: string;
  @Output() isValid: boolean;
  @Input() style: any;
  @Input() inputProps: InputTextProps = {
    messageRequired: '',
    messageValidInput: '',
    placeholder: '',
    hint: '',
    type: 'text',
    disabled: false
  };
  @Input() isRFC: boolean = false;
  constructor() { }

  ngOnInit() {
    if (this.val === undefined || this.val === null) {
      this.val = '';
    }
  }
  sendVal(value: string) {
    this.value.emit(value);
  }
}
