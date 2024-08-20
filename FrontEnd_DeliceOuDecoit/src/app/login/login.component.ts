import { Component, AfterViewInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements AfterViewInit {
  private timeToShowLogin = 400;
  private timeToHideLogin = 200;
  private timeToShowSignUp = 100;
  private timeToHideSignUp = 400;
  private timeToHideAll = 500;

  constructor() {}

  ngAfterViewInit() {
    // Ensure that the DOM is ready before manipulating it
  }

  changeToLogin() {
    const contForms = document.querySelector('.cont_forms') as HTMLElement;
    const contFormLogin = document.querySelector('.cont_form_login') as HTMLElement;
    const contFormSignUp = document.querySelector('.cont_form_sign_up') as HTMLElement;

    if (contForms && contFormLogin && contFormSignUp) {
      contForms.className = 'cont_forms cont_forms_active_login';
      contFormLogin.style.display = 'block';
      contFormSignUp.style.opacity = '0';

      setTimeout(() => {
        contFormLogin.style.opacity = '1';
      }, this.timeToShowLogin);

      setTimeout(() => {
        contFormSignUp.style.display = 'none';
      }, this.timeToHideLogin);
    }
  }

  changeToSignUp() {
    const contForms = document.querySelector('.cont_forms') as HTMLElement;
    const contFormSignUp = document.querySelector('.cont_form_sign_up') as HTMLElement;
    const contFormLogin = document.querySelector('.cont_form_login') as HTMLElement;

    if (contForms && contFormSignUp && contFormLogin) {
      contForms.className = 'cont_forms cont_forms_active_sign_up';
      contFormSignUp.style.display = 'block';
      contFormLogin.style.opacity = '0';

      setTimeout(() => {
        contFormSignUp.style.opacity = '1';
      }, this.timeToShowSignUp);

      setTimeout(() => {
        contFormLogin.style.display = 'none';
      }, this.timeToHideSignUp);
    }
  }

  hiddenLoginAndSignUp() {
    const contForms = document.querySelector('.cont_forms') as HTMLElement;
    const contFormSignUp = document.querySelector('.cont_form_sign_up') as HTMLElement;
    const contFormLogin = document.querySelector('.cont_form_login') as HTMLElement;

    if (contForms && contFormSignUp && contFormLogin) {
      contForms.className = 'cont_forms';
      contFormSignUp.style.opacity = '0';
      contFormLogin.style.opacity = '0';

      setTimeout(() => {
        contFormSignUp.style.display = 'none';
        contFormLogin.style.display = 'none';
      }, this.timeToHideAll);
    }
  }
}
