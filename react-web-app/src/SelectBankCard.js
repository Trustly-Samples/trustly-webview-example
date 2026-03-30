import { useEffect } from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import { Typography, useMediaQuery } from '@mui/material';

export default function SelectBankCard(props) {
  const prefersDarkMode = useMediaQuery('(prefers-color-scheme: dark)');

  const cardStyles = {
    minWidth: 275,
    maxWidth: 550,
    margin: 5,
    backgroundColor: prefersDarkMode ? '#000000' : '#ffffff'
  };

  const typographyStyles = {
    fontSize: 14,
    color: prefersDarkMode ? '#ffffff' : '#000000'
  };

  useEffect(() => {
    const { establishData, TrustlyOptions } = props;

    (async () => {
      const data = await establishData();
      window.Trustly.selectBankWidget(data, TrustlyOptions);
    })();
  }, [props]);

  return (
    <Card sx={cardStyles}>
      <CardContent>
        <Typography sx={typographyStyles} gutterBottom>
          Example of the Select Bank Widget
        </Typography>
        <div id='widget'></div>
      </CardContent>
    </Card>
  );
}
