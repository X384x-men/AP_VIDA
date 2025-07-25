import { CalendarView } from 'angular-calendar';
import {
    SchedulerViewDay, SchedulerViewHour, SchedulerViewHourSegment, CalendarSchedulerEvent,
    SchedulerEventTimesChangedEvent
} from 'angular-calendar-scheduler';

export interface SchedulerEventsInterface {
    changeDate?(date: Date): void;
    changeView?(view: CalendarView): void;
    dateOrViewChanged?(): void;
    isDateValid?(date: Date): boolean;
    dayHeaderClicked?(day: SchedulerViewDay): void;
    hourClicked?(hour: SchedulerViewHour): void;
    segmentClicked?(action: string, segment: SchedulerViewHourSegment): void;
    eventClicked?(action: string, event: CalendarSchedulerEvent): void;
    eventTimesChanged?({ event, newStart, newEnd, type }: SchedulerEventTimesChangedEvent): void;
}
