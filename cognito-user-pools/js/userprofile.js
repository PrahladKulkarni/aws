var myHeaders = new Headers();
myHeaders.set('Cache-Control', 'no-store');
var urlParams = new URLSearchParams(window.location.search);
var tokens;
var domain = "instructor";
var region = "us-west-2";
var appClientId = "5g4ods4hprgt5llbo7o91pbjfe";
var userPoolId = "us-west-2_AFWgAyqiI";
var redirectURI = "https://d1f28333hybq4l.cloudfront.net/cognito/index.html";

//Convert Payload from Base64-URL to JSON
const decodePayload = payload => {
    const cleanedPayload = payload.replace(/-/g, '+').replace(/_/g, '/');
    const decodedPayload = atob(cleanedPayload)
    const uriEncodedPayload = Array.from(decodedPayload).reduce((acc, char) => {
        const uriEncodedChar = ('00' + char.charCodeAt(0).toString(16)).slice(-2)
        return `${acc}%${uriEncodedChar}`
    }, '')
    const jsonPayload = decodeURIComponent(uriEncodedPayload);

    return JSON.parse(jsonPayload)
}

//Parse JWT Payload
const parseJWTPayload = token => {
    const [header, payload, signature] = token.split('.');
    const jsonPayload = decodePayload(payload)

    return jsonPayload
};

//Parse JWT Header
const parseJWTHeader = token => {
    const [header, payload, signature] = token.split('.');
    const jsonHeader = decodePayload(header)

    return jsonHeader
};

//Generate a Random String
const getRandomString = () => {
    const randomItems = new Uint32Array(28);
    crypto.getRandomValues(randomItems);
    const binaryStringItems = randomItems.map(dec => `0${dec.toString(16).substr(-2)}`)
    return binaryStringItems.reduce((acc, item) => `${acc}${item}`, '');
}

//Encrypt a String with SHA256
const encryptStringWithSHA256 = async str => {
    const PROTOCOL = 'SHA-256'
    const textEncoder = new TextEncoder();
    const encodedData = textEncoder.encode(str);
    return crypto.subtle.digest(PROTOCOL, encodedData);
}

//Convert Hash to Base64-URL
const hashToBase64url = arrayBuffer => {
    const items = new Uint8Array(arrayBuffer)
    const stringifiedArrayHash = items.reduce((acc, i) => `${acc}${String.fromCharCode(i)}`, '')
    const decodedHash = btoa(stringifiedArrayHash)

    const base64URL = decodedHash.replace(/\+/g, '-').replace(/\//g, '_').replace(/=+$/, '');
    return base64URL
}

function invokeAPI() {
    fetch(document.getElementById("endpoint").value, {
        method: 'GET',
        headers: {
            'Authorization': document.getElementById("id_token_raw").innerHTML
        }
    })
    .then(response => response.text())    
    .then(text => {
        console.log(text);
        document.getElementById("result").value = text;
        resize("result");
    });
}

function resize(id) {
    elem = document.getElementById(id);
    elem.style.height = "";
    elem.style.height = elem.scrollHeight + "px";
}

// Main Function
async function main() {
    var code = urlParams.get('code');

    //If code not present then request code else request tokens
    if (code == null) {

        // Create random "state"
        var state = getRandomString();
        sessionStorage.setItem("pkce_state", state);

        // Create PKCE code verifier
        var code_verifier = getRandomString();
        sessionStorage.setItem("code_verifier", code_verifier);

        // Create code challenge
        var arrayHash = await encryptStringWithSHA256(code_verifier);
        var code_challenge = hashToBase64url(arrayHash);
        sessionStorage.setItem("code_challenge", code_challenge)

        // Redirtect user-agent to /authorize endpoint
        location.href = "https://" + domain + ".auth." + region + ".amazoncognito.com/oauth2/authorize?response_type=code&state=" + state + "&client_id=" + appClientId + "&redirect_uri=" + redirectURI + "&scope=openid&code_challenge_method=S256&code_challenge=" + code_challenge;
    } else {

        // Verify state matches
        state = urlParams.get('state');
        if (sessionStorage.getItem("pkce_state") != state) {
            alert("Invalid state");
        } else {

            // Fetch OAuth2 tokens from Cognito
            code_verifier = sessionStorage.getItem('code_verifier');
            await fetch("https://" + domain + ".auth." + region + ".amazoncognito.com/oauth2/token?grant_type=authorization_code&client_id=" + appClientId + "&code_verifier=" + code_verifier + "&redirect_uri=" + redirectURI + "&code=" + code, {
                method: 'post',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            })
                .then((response) => {
                    return response.json();
                })
                .then((data) => {

                    // Verify id_token
                    tokens = data;
                    var idVerified = verifyToken(tokens.id_token);
                    Promise.resolve(idVerified).then(function (value) {
                        if (value.localeCompare("verified")) {
                            alert("Invalid ID Token - " + value);
                            return;
                        }
                    });
                    // Display tokens
                    document.getElementById("id_token_raw").innerHTML = tokens.id_token;
                    document.getElementById("id_token").innerHTML = JSON.stringify(parseJWTPayload(tokens.id_token), null, 3);
                });
        }
    }
}
main();