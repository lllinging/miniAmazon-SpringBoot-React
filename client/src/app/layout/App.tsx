import { useEffect, useState } from 'react'
import Header from './Header'
import { Container, CssBaseline, ThemeProvider, createTheme } from '@mui/material'
import { Outlet } from 'react-router-dom';
import { useAppDispatch } from '../store/configureStore';
import { getBasketFromLocalStorage } from '../util/util';
import { fetchCurrentUser } from '../../features/account/accountSlice';
import { setBasket } from '../../features/basket/basketSlice';
import agent from '../api/agent';
import Spinner from './Spinner';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';


function App() {
  const [darkMode, setDarkMode] = useState(false);
  const paletteType = darkMode ? 'dark' : 'light';
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const basket = getBasketFromLocalStorage();
    dispatch(fetchCurrentUser());
    if (basket) {
      agent.Basket.get()
      .then(basket => dispatch(setBasket(basket)))
      .catch(error => console.log(error))
      .finally(() => setLoading(false));
    }else {
      setLoading(false);
    }
  });

  const theme = createTheme({
    palette: {
      mode: paletteType,
    },
  }); 

  function handleThemeChange() {
    setDarkMode(!darkMode);
  } 

  if (loading) return <Spinner message="Getting Basket..." />
  
  return (
    <ThemeProvider theme={theme}>
      <ToastContainer position="bottom-right" hideProgressBar theme="colored"/>
      <CssBaseline />
      <Header darkMode={darkMode} handleThemeChange={handleThemeChange}/>
      <Container sx={{paddingTop: "64px"}}>
        <Outlet />
        {/* <Catalog /> */}
      </Container>
    </ThemeProvider>
  );
}

export default App
