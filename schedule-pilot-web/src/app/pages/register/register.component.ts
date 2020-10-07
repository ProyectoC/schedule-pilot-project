import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ScrollTopService } from '@services/scroll-top/scroll-top.service';
import { RegisterService } from '@services/register/register.service';
import { RoutingConstants } from '@constants/routing-constants';
import { User } from '@models/user';
import { Response } from '@models/response';
import { MessagesService } from '@services/messages/message.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  constructor(
    private router: Router,
    public registerService: RegisterService,
    private scrollTop: ScrollTopService,
    private messageService: MessagesService,
    private spinner: NgxSpinnerService
  ) {}

  ngOnInit(): void {
    this.scrollTop.setScrollTop();
    // this.spinner.show();
  }

  get routingConstants() {
    return RoutingConstants;
  }

  formResult(event) {
    console.log(event);
  }

  registerUser(user: User) {
    this.registerService.registerUser(user).subscribe(
      (bodyResponse: Response) => {
        this.messageService.generateSuccessMessage(
          'Registro',
          'Registro exitoso, por favor revisa tu email para activar tu cuenta'
        );
        this.router
          .navigate([RoutingConstants.URL_AUTHENTICATION])
          .then(() => {});
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
