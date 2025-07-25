import { CalendarView, CalendarEvent } from 'angular-calendar';
import { Subject } from 'rxjs';
import { SchedulerDaysProps } from './scheduler-days-props';
import { SchedulerButtons } from './scheduler-buttons';

export interface SchedulerProps {
    view?: CalendarView;
    viewDate?: Date;
    refresh?: Subject<any>;
    locale?: string;
    weekStartsOn?: number;
    startsWithToday?: boolean;
    activeDayIsOpen?: boolean;
    excludeDays?: number[];
    events?: CalendarEvent[];
    days?: SchedulerDaysProps;
    actions?: SchedulerButtons;
}
