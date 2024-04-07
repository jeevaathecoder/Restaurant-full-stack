import React, {useEffect, useState} from 'react';
import axios from "axios";

const UserList = () => {

    const [users, setUsers] = useState([]);

    useEffect(() => {
        loadUsers();
    }, []);


    const loadUsers = async () => {
        const result = await axios.get("http://localhost:8080/user")
        setUsers(result.data);
        console.log(result.data)
    }


    return (
        <>
            <div className="flex justify-between bg-blue-500 h-20 px-5 py-2">
                <h1 className="font-bold text-white align-middle py-5">User Management System</h1>
                <button
                    className="text-white font-medium bg-blue-800 rounded-2xl px-5 py-5 cursor-pointer hover:bg-blue-900">
                    Add User
                </button>
            </div>


            <div className="flex justify-center">
                <table className="table-auto border-collapse m-4 w-auto">
                    <thead>
                    <tr className="bg-green-100">
                        <th className="border border-green-600 px-4 py-2">Name</th>
                        <th className="border border-green-600 px-4 py-2">UserName</th>
                        <th className="border border-green-600 px-4 py-2">Email ID</th>

                    </tr>
                    </thead>
                    <tbody>

                    {
                        users.map((user, index) => (
                            <tr key={index}>
                                <td  className="border border-green-600 px-4 py-2">{user.name}</td>
                                <td  className="border border-green-600 px-4 py-2">{user.userName}</td>
                                <td  className="border border-green-600 px-4 py-2">{user.email}</td>
                            </tr>
                        ))
                    }


                    </tbody>
                </table>
            </div>


        </>
    )
        ;
};

export default UserList;