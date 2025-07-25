import { Component, OnInit, Output, Input, EventEmitter, OnChanges, AfterViewInit } from '@angular/core';
import { UntypedFormControl, Validators, FormGroupDirective, NgForm, UntypedFormGroup, UntypedFormBuilder } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { InputTextProps } from 'src/app/core/interface/input-options/input';

export class StateMatcher implements ErrorStateMatcher {
  isErrorState(control: UntypedFormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}
@Component({
  selector: 'app-required-input-text',
  templateUrl: './required-input-text.component.html',
  styleUrls: ['./required-input-text.component.css']
})
export class RequiredInputTextComponent implements OnInit, AfterViewInit, OnChanges {
  options: UntypedFormGroup;
  @Output() value = new EventEmitter<string>();
  @Input() val: string;
  @Output() isValid: boolean;
  @Input() data: InputTextProps = {
    messageRequired: '',
    messageValidInput: '',
    placeholder: '',
    hint: '',
    type: 'text'
  };
  @Input() style: any;
  @Input() info: boolean;
  @Input() messaggeInfo: string = '';
  @Input() isRFC: boolean = false;
  startBlankSpace = false;
  validInputFormControl = new UntypedFormControl('', [
    Validators.required,
    Validators.minLength(1)
  ]);

  matcher = new StateMatcher();
  constructor(private fb: UntypedFormBuilder) {
  }
  ngAfterViewInit() {
  }

  ngOnInit() {
    this.options = this.fb.group({
      addressLine1: ['', Validators.required],
      hideRequired: false,
      floatLabel: 'auto',
    });
  }
  ngOnChanges(event) {
    if (this.val === undefined || this.val === null) {
      this.val = '';
    }
  }
  sendVal(event) {
    if (event.target.value > 0) {
      this.value.emit(event.target.value);
    } else {
      this.value.emit(event.target.value);
      this.startBlankSpace = true;
    }
  }

  mayus(value) {
    console.log('entra');
    if(this.isRFC){
      value.target.value = value.target.value.replace(/[^0-9a-zA-ZñÑáéíóúÁÉÍÓÚ]/g,''); 
      let value2 = value.target.value.replace(/[^0-9a-zA-ZñÑáéíóúÁÉÍÓÚ]/g,''); 
      this.value.emit(value2.toUpperCase());
    }
  }

}
