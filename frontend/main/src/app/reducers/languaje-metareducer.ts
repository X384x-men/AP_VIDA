import { ActionReducer, Action, MetaReducer } from '@ngrx/store';
import { merge, pick } from 'lodash-es';

import { Languaje } from './app-reducer/app-reducer';
import { StateStorageService } from '../core/services/state-storage/state-storage.service';

export function storageMetaReducer(
    saveKeys: string[], localStorageKey: string, storageService: StateStorageService): MetaReducer<Languaje> {
    let onInit = true; // after load/refresh…
    return (reducer: ActionReducer<Languaje, Action>): ActionReducer<Languaje, Action> => {

        return (state, action) => {
            const nextState = reducer(state, action);
            // init the application state.
            if (onInit) {
                onInit = false;
                const savedState = storageService.getSavedState(localStorageKey);
                return merge(nextState, savedState);
            }

            // save the next state to the application storage.
            const stateToSave = pick(nextState, saveKeys);
            storageService.setSavedState(stateToSave, localStorageKey);
            return nextState;
        };
    };
}
