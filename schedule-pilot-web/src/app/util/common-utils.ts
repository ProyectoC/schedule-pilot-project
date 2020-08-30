import { environment } from '../../environments/environment';

export class CommonUtils {
  static getRandomColorHex(): string {
    const hex = environment.components.dashboard['random.letters'];
    let color = '#';
    for (let i = 1; i <= 6; i++) {
      color += hex[Math.floor(Math.random() * 16)];
    }
    return color;
  }
}
