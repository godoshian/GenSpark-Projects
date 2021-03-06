import styled from 'styled-components';
//  npm add styled-components

import {Link} from 'react-router-dom';
// npm install react-router-dom

// DON'T FORGET TO USE: npm i react-icons

import background from './../assets/northern_lights.jpg'

// define the colors
export const colors = {
    primary: "#fff",
    theme: "#BE185D",
    light1: "#F3F4F6",
    light2: "#E5E7EB",
    dark1: "#1F2937",
    dark2: "#4B5563",
    dark3: "#9CA3AF",
    red: "#DC2626"
}


export const StyledContainer = styled.div`
margin: 0;
display: flex;
justify-content: center;
align-items: center;
background: url(${background});
background-size: cover;
background-attachment: fixed;
min-height: 100vh;
`;

export const StyledTitle = styled.h2`
font-size: ${(props) => props.size}px;
text-align: center;
color: ${(props) => props.color ? props.color : colors.primary};
padding: 5px;
margin-bottom: 20px;
`;

export const StyledSubTitle = styled.p`
font-size: ${(props) => props.size}px;
text-align: center;
color: ${(props) => props.color ? props.color : colors.primary};
padding: 5px;
margin-bottom: 25px;
`;

export const Avatar = styled.div`
width: 80px;
height: 80px;
border-radius: 50px;
background-image: url(${props => props.image});
background-size: cover;
background-position: center;
margin: auto;
`;

export const StyledButton = styled(Link)`
padding: 10px;
width: 150px;
background-color: transparent;
font-size: 16px;
border: 3px solid ${colors.primary};
border-radius: 25px;
color: ${colors.primary};
text-align: center;
transition: ease-in-out 0.3s;

&:hover {
    background-color:${colors.primary};
    color: ${colors.theme};
}
`;

export const ButtonGroup = styled.div`
display: flex;
justify-content: space-around;
flex-direction: row;
`;

export const StyledTextInput = styled.input`
padding: 15px;
width: 275px;
padding-left: 50px;
letter-spacing: 1px;
color: ${colors.light2};
border: 0;
display: block;
margin: 5px auto 10px auto;
transition: ease-in-out 0.3s;

${(props) => props.invalid && `background-color: ${colors.red}; color: ${colors.primary};`}

&:focus {
    background-color: ${colors.dark2};
    color: ${colors.primary};
}
`;

export const StyledLabel = styled.p`
text-align: left;
font-size: 14px;
font-weight: bold;
`;

export const StyledFormArea = styled.div`
background-color: ${props => props.bg || colors.light1};
text-align: center;
padding: 45px 55px;
`;

export const StyledFormButton = styled.button`
padding: 10px;
width: 150px;
background-color: transparent;
font-size: 16px;
border: 3px solid ${colors.theme};
border-radius: 25px;
color: ${colors.theme};
transition: ease-in-out 0.3s;

&:hover {
    background-color:${colors.theme};
    color: ${colors.primary};
}
`;

// npm i react-icons
export const StyledIcon = styled.p`
color: ${colors.dark1};
position: absolute;
font-size: 20px;
top: 35px;
${(props) => props.right && `right: 15px;`};
${(props) => !props.right && `left: 15px;`};
`;

export const ExtraText = styled.p`
font-size: ${(props) => props.size}px;
text-align: center;
color: ${(props) => props.color? props.color : colors.dark2};
padding: 2px;
margin-top: 10px
`;