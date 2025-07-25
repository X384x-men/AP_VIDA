import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-div-error-message',
  templateUrl: './div-error-message.component.html',
  styleUrls: ['./div-error-message.component.css']
})
export class DivErrorMessageComponent implements OnInit {
  @Input() enableDiv = false;
  @Input() message = '';
  constructor() { }

  ngOnInit() {
  }

}
