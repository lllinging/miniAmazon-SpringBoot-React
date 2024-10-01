import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './app/layout/App.tsx'
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';

import './app/layout/index.css'
import { RouterProvider } from 'react-router-dom';
import { router } from './app/router/Router.tsx';
import { store } from './app/store/configureStore.ts';
import { Provider } from 'react-redux';
import { loadStripe } from '@stripe/stripe-js';
import { Elements } from '@stripe/react-stripe-js';

const stripePromise = loadStripe('pk_test_51Q4lg3L52f7XexE5idGhTVqowlA8txNPDBAbyF0aSHPxAu38LF17kC1OYDwE4F2iwyMMLnLFXAzwO5Edp9wIinEU00HnniJDOt');

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    {/* <App /> */}
    <Provider store={store}>
      <Elements stripe={stripePromise}>
        <RouterProvider router={router} />
      </Elements>
    </Provider>
  </React.StrictMode>,
)
