import { PasswordUser } from './password-user';
import { RolUser } from './rol-user';

export class User {
    public firstName: string;
    public lastName: string;
    public identification: number;
    public email: string;
    public userName: string;
    public passwordSecurityEntity: PasswordUser;
    public rolSecurityEntity: RolUser;

    constructor() {
        this.passwordSecurityEntity = new PasswordUser();
        this.rolSecurityEntity = new RolUser();
    }
}
