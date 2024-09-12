import { Box, Grid, Typography, Link } from "@mui/material";

import backgroundPicture from "../../../public/images/backgroundPicture/philippe-murray-pietsch-eQWPSFC6dfo-unsplash.jpg"; 

export default function ContactPage() {

  return (
    <Box
      sx={{
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        height: '100vh',
        textAlign: 'center',
        padding: 3,
        backgroundColor: '#f5f5f5', 
      }}
    >
      <Grid container spacing={2} alignItems="center">
        <Grid item xs={12} md={6}>
          <img
            src={backgroundPicture}
            alt="Contact Us"
            style={{
              width: '100%',
              height: 'auto',
              borderRadius: '8px',
              boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)', 
            }}
          />
        </Grid>

        <Grid item xs={12} md={6}>
          <Box sx={{ textAlign: 'left', padding: 3 }}>
            <Typography variant="h4" component="h2" sx={{ fontWeight: 'bold', color: '#1976d2', marginBottom: 2 }}>
              Contact Us
            </Typography>

            <Typography variant="body1" component="p" sx={{ marginBottom: 2 }}>
              If you have any questions or need further assistance, please feel free to reach out to us. We’re here to help!
            </Typography>

            <Typography variant="h6" component="h3" sx={{ fontWeight: 'bold', marginBottom: 1 }}>
              Website Information
            </Typography>
            <Typography variant="body2" component="p" sx={{ marginBottom: 2 }}>
              Website: <Link href="https://www.sportscenter.com" target="_blank">www.sportscenter.com</Link><br />
              Email: <Link href="mailto:support@sportscenter.com">support@sportscenter.com</Link>
            </Typography>

            <Typography variant="h6" component="h3" sx={{ fontWeight: 'bold', marginBottom: 1 }}>
              Contact Information
            </Typography>
            <Typography variant="body2" component="p" sx={{ marginBottom: 2 }}>
              Phone: (123) 456-7890<br />
              Address: 123 Sports Center Blvd, Suite 100, Sportstown, ST 12345
            </Typography>
          </Box>
        </Grid>
      </Grid>
    </Box>
  );
}