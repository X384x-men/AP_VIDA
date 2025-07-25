import { NgModule } from '@angular/core';
import { SafePipe, SafeHtmlPipe } from './sanitizer';
import { CurrencyPipe, DateFormat, DateFromNumber, formDate, formDate2, ReplaceCharacter, ReversePipe } from './dateFormat';

@NgModule({
    declarations: [
        SafePipe,
        DateFormat,
        SafeHtmlPipe,
        DateFromNumber,
        formDate,
        formDate2,
        ReversePipe,
        CurrencyPipe,
        ReplaceCharacter
    ],
    exports: [
        SafePipe,
        DateFormat,
        SafeHtmlPipe,
        DateFromNumber,
        formDate,
        formDate2,
        ReversePipe,
        CurrencyPipe,
        ReplaceCharacter
    ],
    imports: [
    ]
  })
  export class PipeModule { }