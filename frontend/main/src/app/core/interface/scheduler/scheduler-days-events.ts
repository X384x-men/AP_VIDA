export interface SchedulerDaysEvents {
    dayModifier: () => void;
    hourModifier: () => void;
    segmentModifier: () => void;
    eventModifier: () => void;
}
