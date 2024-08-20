import { useState } from 'react'
import Catalog from '../../features/catalog/Catalog'
import Header from './Header'
import { Container, CssBaseline, ThemeProvider, createTheme } from '@mui/material'
import { Outlet } from 'react-router-dom';

function App() {
  const [darkMode, setDarkMode] = useState(false);
  const paletteType = darkMode ? 'dark' : 'light';

  const theme = createTheme({
    palette: {
      mode: paletteType,
    },
  }); 

  function handleThemeChange() {
    setDarkMode(!darkMode);
  } 

  return (
    <ThemeProvider theme={theme}>
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
