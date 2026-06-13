import React, { useEffect, useState } from 'react';
import api from '../api/axios';
import {
    Users,
    Package,
    Trash2,
    ShieldCheck,
    Loader2,
    AlertTriangle,
} from 'lucide-react';

const AdminDashboard = () => {
    const [users, setUsers] = useState([]);
    const [items, setItems] = useState([]);
    const [loading, setLoading] = useState(true);

    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [deleteType, setDeleteType] = useState(null);
    const [deleteId, setDeleteId] = useState(null);
    const [deleting, setDeleting] = useState(false);

    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        setLoading(true);
        setError('');

        try {
            const usersResponse = await api.get('/admin/users');
            const itemsResponse = await api.get('/admin/items');

            setUsers(usersResponse.data);
            setItems(itemsResponse.data);
        } catch (error) {
            setError(
                error.response?.data?.message ||
                'Admin məlumatları yüklənərkən xəta baş verdi.'
            );
        } finally {
            setLoading(false);
        }
    };

    const openDeleteModal = (type, id) => {
        setDeleteType(type);
        setDeleteId(id);
        setShowDeleteModal(true);
        setError('');
        setSuccess('');
    };

    const closeDeleteModal = () => {
        setShowDeleteModal(false);
        setDeleteType(null);
        setDeleteId(null);
    };

    const handleConfirmDelete = async () => {
        if (!deleteType || !deleteId) return;

        setDeleting(true);
        setError('');
        setSuccess('');

        try {
            if (deleteType === 'user') {
                await api.delete(`/admin/users/${deleteId}`);
                setSuccess('İstifadəçi uğurla silindi.');
            } else {
                await api.delete(`/admin/items/${deleteId}`);
                setSuccess('Elan uğurla silindi.');
            }

            closeDeleteModal();
            await fetchData();

            setTimeout(() => {
                setSuccess('');
            }, 3000);
        } catch (error) {
            setError(
                error.response?.data?.message ||
                'Silmə əməliyyatı zamanı xəta baş verdi.'
            );
        } finally {
            setDeleting(false);
        }
    };

    if (loading) {
        return (
            <div className="min-h-screen flex justify-center items-center">
                <Loader2 size={48} className="animate-spin text-primary-600" />
            </div>
        );
    }

    return (
        <div className="min-h-screen px-4 py-12">
            <div className="max-w-7xl mx-auto">
                <div className="flex items-center mb-10">
                    <div className="w-16 h-16 rounded-3xl bg-primary-600 text-white flex items-center justify-center shadow-xl shadow-primary-500/20 mr-5">
                        <ShieldCheck size={34} />
                    </div>

                    <div>
                        <h1 className="text-4xl font-bold text-slate-900 dark:text-white">
                            Admin Panel
                        </h1>

                        <p className="text-slate-500 dark:text-slate-400 mt-2">
                            İstifadəçiləri və elanları idarə edin
                        </p>
                    </div>
                </div>

                {error && (
                    <div className="mb-8 p-4 bg-rose-50 dark:bg-rose-900/20 border border-rose-200 dark:border-rose-800 rounded-2xl text-rose-600 dark:text-rose-400 text-sm font-medium">
                        {error}
                    </div>
                )}

                {success && (
                    <div className="mb-8 p-4 bg-emerald-50 dark:bg-emerald-900/20 border border-emerald-200 dark:border-emerald-800 rounded-2xl text-emerald-600 dark:text-emerald-400 text-sm font-medium">
                        {success}
                    </div>
                )}

                <div className="grid md:grid-cols-2 gap-6 mb-10">
                    <div className="bg-white dark:bg-slate-900 rounded-3xl p-6 border border-slate-200 dark:border-slate-800 shadow-lg hover:shadow-xl transition-all duration-300 hover:-translate-y-1">
                        <div className="flex items-center justify-between">
                            <div>
                                <p className="text-slate-500 dark:text-slate-400 font-semibold">
                                    İstifadəçilər
                                </p>

                                <h2 className="text-4xl font-extrabold text-slate-900 dark:text-white mt-2">
                                    {users.length}
                                </h2>
                            </div>

                            <Users size={42} className="text-primary-600" />
                        </div>
                    </div>

                    <div className="bg-white dark:bg-slate-900 rounded-3xl p-6 border border-slate-200 dark:border-slate-800 shadow-lg hover:shadow-xl transition-all duration-300 hover:-translate-y-1">
                        <div className="flex items-center justify-between">
                            <div>
                                <p className="text-slate-500 dark:text-slate-400 font-semibold">
                                    Elanlar
                                </p>

                                <h2 className="text-4xl font-extrabold text-slate-900 dark:text-white mt-2">
                                    {items.length}
                                </h2>
                            </div>

                            <Package size={42} className="text-primary-600" />
                        </div>
                    </div>
                </div>

                <div className="bg-white dark:bg-slate-900 rounded-3xl p-6 border border-slate-200 dark:border-slate-800 shadow-lg mb-10">
                    <h2 className="text-2xl font-bold mb-6 text-slate-900 dark:text-white">
                        İstifadəçilər
                    </h2>

                    <div className="space-y-4">
                        {users.length > 0 ? (
                            users.map((user) => (
                                <div
                                    key={user.id}
                                    className="flex flex-col md:flex-row md:items-center md:justify-between gap-4 border-b border-slate-200 dark:border-slate-800 pb-4"
                                >
                                    <div>
                                        <h3 className="font-bold text-slate-900 dark:text-white">
                                            {user.name} {user.surname}
                                        </h3>

                                        <p className="text-slate-500 dark:text-slate-400 text-sm">
                                            {user.email}
                                        </p>

                                        <p className="text-slate-500 dark:text-slate-400 text-sm">
                                            {user.phone}
                                        </p>
                                    </div>

                                    <button
                                        onClick={() => openDeleteModal('user', user.id)}
                                        className="inline-flex items-center justify-center px-4 py-3 bg-rose-50 dark:bg-rose-900/20 text-rose-600 dark:text-rose-400 rounded-2xl font-bold hover:bg-rose-100 dark:hover:bg-rose-900/40 transition-all"
                                    >
                                        <Trash2 size={18} className="mr-2" />
                                        Sil
                                    </button>
                                </div>
                            ))
                        ) : (
                            <p className="text-slate-500 dark:text-slate-400">
                                İstifadəçi tapılmadı.
                            </p>
                        )}
                    </div>
                </div>

                <div className="bg-white dark:bg-slate-900 rounded-3xl p-6 border border-slate-200 dark:border-slate-800 shadow-lg">
                    <h2 className="text-2xl font-bold mb-6 text-slate-900 dark:text-white">
                        Elanlar
                    </h2>

                    <div className="space-y-4">
                        {items.length > 0 ? (
                            items.map((item) => (
                                <div
                                    key={item.id}
                                    className="flex flex-col md:flex-row md:items-center md:justify-between gap-4 border-b border-slate-200 dark:border-slate-800 pb-4"
                                >
                                    <div>
                                        <h3 className="font-bold text-slate-900 dark:text-white">
                                            {item.title}
                                        </h3>

                                        <p className="text-slate-500 dark:text-slate-400 text-sm">
                                            Sahib: {item.ownerEmail}
                                        </p>

                                        <p className="text-slate-500 dark:text-slate-400 text-sm">
                                            Şəhər: {item.cityName}
                                        </p>
                                    </div>

                                    <button
                                        onClick={() => openDeleteModal('item', item.id)}
                                        className="inline-flex items-center justify-center px-4 py-3 bg-rose-50 dark:bg-rose-900/20 text-rose-600 dark:text-rose-400 rounded-2xl font-bold hover:bg-rose-100 dark:hover:bg-rose-900/40 transition-all"
                                    >
                                        <Trash2 size={18} className="mr-2" />
                                        Sil
                                    </button>
                                </div>
                            ))
                        ) : (
                            <p className="text-slate-500 dark:text-slate-400">
                                Elan tapılmadı.
                            </p>
                        )}
                    </div>
                </div>
            </div>

            {showDeleteModal && (
                <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-slate-950/60 backdrop-blur-sm">
                    <div className="bg-white dark:bg-slate-900 rounded-[2.5rem] max-w-md w-full p-8 border border-slate-200 dark:border-slate-800 shadow-2xl">
                        <div className="w-16 h-16 bg-rose-100 dark:bg-rose-900/20 text-rose-600 rounded-full flex items-center justify-center mb-6 mx-auto">
                            <AlertTriangle size={32} />
                        </div>

                        <h3 className="text-2xl font-bold text-slate-900 dark:text-white text-center mb-2">
                            Silmə əməliyyatı
                        </h3>

                        <p className="text-slate-500 dark:text-slate-400 text-center mb-8">
                            {deleteType === 'user'
                                ? 'Bu istifadəçini silmək istədiyinizə əminsiniz?'
                                : 'Bu elanı silmək istədiyinizə əminsiniz?'}
                        </p>

                        <div className="flex gap-4">
                            <button
                                onClick={closeDeleteModal}
                                disabled={deleting}
                                className="flex-1 py-4 bg-slate-100 dark:bg-slate-800 text-slate-600 dark:text-slate-300 rounded-2xl font-bold hover:bg-slate-200 dark:hover:bg-slate-700 transition-all"
                            >
                                Ləğv et
                            </button>

                            <button
                                onClick={handleConfirmDelete}
                                disabled={deleting}
                                className="flex-1 py-4 bg-rose-600 text-white rounded-2xl font-bold hover:bg-rose-700 transition-all shadow-xl shadow-rose-500/20 flex items-center justify-center"
                            >
                                {deleting ? (
                                    <Loader2 size={24} className="animate-spin" />
                                ) : (
                                    'Bəli, sil'
                                )}
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default AdminDashboard;