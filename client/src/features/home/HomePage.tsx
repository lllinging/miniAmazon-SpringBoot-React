import { Typography, Box, Button, Grid } from "@mui/material";
import { useNavigate } from "react-router-dom"; 

import backgroundPicture from "../../../public/images/backgroundPicture/john-fornander-4R9CcBdQTEg-unsplash.jpg"; 

export default function HomePage() {
    const navigate = useNavigate();

    const handleGoToStore = () => {
        navigate("/store");
    };

    return (
        <Box
            sx={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '100vh',
                textAlign: 'center',
                // backgroundColor: '#e3f2fd',
                padding: 3,
            }}
        >
            <Grid container spacing={2} alignItems="center">
                <Grid item xs={12} md={6}>
                    <img
                        src={backgroundPicture}
                        alt="Sports"
                        style={{
                            width: '100%',
                            height: 'auto',
                            borderRadius: '8px',
                        }}
                    />
                </Grid>

                <Grid item xs={12} md={6}>
                    <Typography variant="h2" component="h1" sx={{ fontWeight: 'bold', color: '#1976d2', marginBottom: 2 }}>
                        Welcome to the Sports Center
                    </Typography>

                    <Button
                        variant="contained"
                        color="primary"
                        size="large"
                        onClick={handleGoToStore}
                        sx={{ mt: 3 }}
                    >
                        Go to Store
                    </Button>
                </Grid>
            </Grid>
        </Box>
    );

}
