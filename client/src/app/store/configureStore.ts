import { configureStore } from "@reduxjs/toolkit";
import { Store } from 'redux';
import { useDispatch, TypedUseSelectorHook, useSelector } from "react-redux";
import { basketSlice } from "../../features/basket/basketSlice";
import { accountSlice } from "../../features/account/accountSlice";

export const store = configureStore({
    reducer: {
        basket: basketSlice.reducer,
        account: accountSlice.reducer
    }
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;// This is the type of the dispatch function that is returned by the store. It is used to dispatch actions to the store.

export const useAppDispatch = () => useDispatch<AppDispatch>();
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;// This is a hook that allows you to extract data from the Redux store state. It is a typed version of the useSelector hook from the react-redux library. It is used to select data from the store state.