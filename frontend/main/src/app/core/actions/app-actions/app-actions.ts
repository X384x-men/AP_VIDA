import { Action } from '@ngrx/store';
import { LanguageTheme } from '../../interface/app-state/app-state';


export const LOAD_LANGUAGE = '[LANGUAGE] Load';
export const CHANGE_LANGUAGE = '[CHANGE] Remove';

export class LoadLanguage implements Action {
    readonly type = LOAD_LANGUAGE;
    constructor(public payLoad: LanguageTheme) {
    }
}

export class ChangeLanguage implements Action {
    readonly type = CHANGE_LANGUAGE;
    constructor(public payLoad: string) {
    }
}

export type Actions = LoadLanguage | ChangeLanguage;
