export class ItemFormTemplate {
    
    public label: string;
    public value: any;
    public required: boolean;

    constructor(label: string, value: any, required: boolean) {
        this.label = label;
        this.value = value;
        this.required = required;
    }
    
}