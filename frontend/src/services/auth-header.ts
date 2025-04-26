export default function authHeader() {
  const userStr = window.localStorage.getItem("userAuth");
  console.log('userStr ', userStr);
  let user = null;
  if (userStr)
    user = JSON.parse(userStr);

  if (user && user.token) {
    return { Authorization: 'Bearer ' + user.token };
  } else {
    return { Authorization: '' };
  }
}