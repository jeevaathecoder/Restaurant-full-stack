import axios from "axios";

export const api = axios.create({
    baseURL: "http://localhost:9192"
})

export async function addRoom(photo,roomType,roomPrice){
    const formData = new FormData();
    formData.append("photo", photo);
    formData.append("roomType", roomType);
    formData.append("roomPrice", roomPrice);
    return await api.post("/addRoom", formData);
 }