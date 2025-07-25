import { NgModule, LOCALE_ID } from '@angular/core';
import { CommonModule, registerLocaleData } from '@angular/common';
import { SchedulerHeaderComponent } from './scheduler-header/scheduler-header.component';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { SchedulerModule } from 'angular-calendar-scheduler';
import { MaterialModule } from '../../material/material-module';
import localeFr from '@angular/common/locales/es-MX';
import localeFrExtra from '@angular/common/locales/extra/es-MX';
registerLocaleData(localeFr, 'es-MX', localeFrExtra);
@NgModule({
  imports: [
    CommonModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory
    }),
    SchedulerModule.forRoot({ locale: 'es', headerDateFormat: 'daysRange' })
  ],
  providers: [{ provide: LOCALE_ID, useValue: 'es-MX' }],
  declarations: [SchedulerHeaderComponent],
  exports: [SchedulerHeaderComponent,
    CalendarModule,
    MaterialModule,
  ]
})
export class CalendarSchedulerModule { }
