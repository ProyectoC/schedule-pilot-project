import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RoutingConstants } from '@constants/routing-constants';
import { MessagesService } from '@services/messages/message.service';
import { ScrollTopService } from '@services/scroll-top/scroll-top.service';
import { Validations } from '@utils/forms/validations';
import { BaseFormComponent } from 'app/shared/forms/components/base-form.component';
import { ForgotPassword } from '@models/forgot-password';
import { AuthenticationService } from '@services/authentication/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {

  public isLoading: boolean;

  constructor(private scrollTop: ScrollTopService, private messageService: MessagesService,
    public authService: AuthenticationService, private router: Router) {
  }

  ngOnInit(): void {
    this.scrollTop.setScrollTop();
  }

  get routingConstants() {
    return RoutingConstants;
  }

  public submitModal($event: ForgotPassword): void {
    this.messageService.generateConfirmMessage(null, 'Iniciaras el proceso de restauración de contraseña.').then(
      (responseUser: boolean) => {
        if (responseUser) {
          this.isLoading = true;
          this.authService.restartPasswordUser($event).subscribe((bodyResponse: any) => {
            this.isLoading = false;
            this.messageService.generateSuccessMessage('Restaurar Contraseña', 'La contraseña se ha restaurada correctamente!');
            this.router.navigate([this.routingConstants.URL_AUTHENTICATION]).then(() => { });
          });
        }
      });
  }
}
