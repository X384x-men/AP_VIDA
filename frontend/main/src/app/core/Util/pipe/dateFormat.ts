import { Pipe, PipeTransform } from '@angular/core';
import { Smartwfm } from '../smartwfm/smartwfm';
import moment from 'moment';
import Currency from "currency.js"

const ARRAY_MESES = { '0':'Enero','1':'Febrero','2':'Marzo',
    '3':'Abril','4':'Mayo','5':'Junio',
    '6':'Julio','7':'Agosto','8':'Septiembre',
    '9':'Octubre','10':'Noviembre','11':'Diciembre' 
    }; 

@Pipe({
    name: 'dateFormat'
  })
  export class DateFormat implements PipeTransform {
  
    constructor() { }

    transform(value : Date) {
        let day = value.getDate();
        let month = value.getMonth();
        let year = value.getFullYear();

      return (day + ' ' + ARRAY_MESES[month] + ' ' + year );
    }
  
  }

  @Pipe({
    name: 'dateFromNumber'
  })
  export class DateFromNumber implements PipeTransform {
  
    constructor() { }

    transform(date : number) {
      if(date){
        return Smartwfm.getDateFromNumber(date);
      }else{
        return " "
      }
      
    }
  }

  @Pipe({
    name: 'formatDate'
  })
  export class formDate implements PipeTransform {
  
    constructor() { }

    transform(date : string) {
      let anio = date.slice(0, 4);
      let mes = date.slice(4, 6);
      let dia = date.slice(6, 8);
      let date1 = anio+'-'+(Number(mes))+'-'+dia;
      let dateFormat1 = moment(date1).format('DD/MM/YYYY');
      let dateFormat2 = moment(date1).add(1, 'months').format('DD/MM/YYYY');
      return dateFormat1 + ' - ' + dateFormat2;
    }
  }

  @Pipe({
    name: 'formatDate2'
  })
  export class formDate2 implements PipeTransform {
  
    constructor() { }

    transform(date : string) {
      let anio = date.slice(0, 4);
      let mes = date.slice(4, 6);
      let dia = date.slice(6, 8);
      let date1 = anio+'-'+(Number(mes))+'-'+dia;
      let dateFormat1 = moment(date1).format('DD/MM/YYYY');
      return dateFormat1;
    }
  }


  @Pipe({ name: 'reverse' })

  export class ReversePipe implements PipeTransform {
    transform(value) {
      return value.slice().reverse();
    }
}


@Pipe({ name: 'currency' })

export class CurrencyPipe implements PipeTransform {
  transform(value) {
    return Currency(value).format();
  }
}

@Pipe({ name: 'replaceCharacter' })

export class ReplaceCharacter implements PipeTransform {
  transform(value: any, args: any, args2: any) {
    return value.replaceAll(args, args2);
  }
}
