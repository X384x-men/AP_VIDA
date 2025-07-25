export interface DateValue {
    stringDate?: string;
    date?: Date;
}
export interface DatesRequest {
    dateInicio: DateValue;
    dateFin: DateValue;
}
export interface DatesRange {
    stringDateIni?: string;
    stringDateFin?: string;
    dateInicio?: Date;
    dateFin?: Date;
}
export interface Year {
    añoStr?: string;
}

export interface SimpleDate {
    lastYear: number;
    firstYear: number;
    firstMonthName: string;
    LastMonthmonthName: string;
    firstDay: number;
    lastDay: number;
    fullDate: string;
}