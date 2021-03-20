export type Task = {
    uuid: string;
    requester: string;
    content: string;
    createdAt: Date;
    categoryName: string;
}

export type TaskCategory = {
    id: number;
    alias: string;
    name: string;
}