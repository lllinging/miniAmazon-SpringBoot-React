import * as yup from 'yup';

export const ValidationRules = [
    yup.object({
        firstName: yup.string().required('First Name is required'),
        lastName: yup.string().required('Last Name is required'),
        address1: yup.string().required('Address1 is required'),
        address2: yup.string().optional(),
        city: yup.string().required('City is required'),
        state: yup.string().required('State is required'),
        zip: yup.string().required('Zip Code is required'),
        country: yup.string().required('Country is required'),
    }),
    yup.object(),
    yup.object({
        cardName: yup.string().required(),
        cardNumber: yup.string().required(),
        expDate: yup.string().required(),
        cvv: yup.string().required(),
    })
]