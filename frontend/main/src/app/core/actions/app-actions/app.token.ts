import { InjectionToken } from '@angular/core';
import { StoreConfig } from '@ngrx/store';
import * as appState from '../../interface/app-state/app-state';
import * as appActions from '../app-actions/app-actions';
import { HttpClient } from '@angular/common/http';

export const LANGUAJE_STORAGE_KEYS =
    new InjectionToken<keyof appState.LanguageTheme>('LanguajeStorageKeys');
export const LANGUAJE_LOCAL_STORAGE_KEY =
    new InjectionToken<string[]>('LanguajeStorage');
export const LANGUAJE_CONFIG_TOKEN =
    new InjectionToken<StoreConfig<appState.LanguageTheme, appActions.Actions>>('LanguajeConfigToken');

export const HTTP_CLIENT = new InjectionToken<HttpClient>('httpClient');

// token for the state keys.
export const ROOT_STORAGE_KEYS = new InjectionToken<string[]>('StoreKeys');
// token for the localStorage key.
export const ROOT_LOCAL_STORAGE_KEY = new InjectionToken<string[]>('appStorage');
