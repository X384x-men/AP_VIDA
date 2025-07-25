import { Component, OnInit, Input, Output, EventEmitter, OnChanges } from '@angular/core';
import { CalendarView } from 'angular-calendar';
import { SchedulerProps } from 'src/app/core/interface/scheduler/scheduler-props';

import { SimpleDate } from 'src/app/core/interface/date/DateValue';
import { Smartwfm } from 'src/app/core/Util/smartwfm/smartwfm';
@Component({
  selector: 'app-scheduler-header',
  templateUrl: './scheduler-header.component.html',
  styleUrls: ['./scheduler-header.component.css']
})
export class SchedulerHeaderComponent implements OnInit, OnChanges {



  @Input() scheDulerProps: SchedulerProps;

  @Input() view: string;

  @Input() locale = 'es';

  @Input() viewDate: Date;

  @Output() viewChange: EventEmitter<string> = new EventEmitter();

  @Output() viewDateChange: EventEmitter<Date> = new EventEmitter();
  simpleDate: SimpleDate;



  constructor() { }

  ngOnInit() {

  }
  ngOnChanges() {
    this.simpleDate = Smartwfm.buildDate(this.viewDate);
  }
  update() {
    alert('En construccion');
  }

  setView(view: CalendarView) {
    this.view = view;
  }
}
