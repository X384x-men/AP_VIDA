import { CalendarEventAction } from 'angular-calendar';
export interface SchedulerButtons {
    prevBtnDisabled: boolean;
    nextBtnDisabled: boolean;
    actions: CalendarEventAction[];
}
