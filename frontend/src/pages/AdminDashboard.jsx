import React, { useEffect, useState } from 'react';
import api from '../api/axios';
import {
    Users,
    Package,
    Trash2,
    ShieldCheck,
    Loader2,
    AlertTriangle,
    Mail,
    Phone,
    MapPin,
    Crown,
    Search,
} from 'lucide-react';

const AdminDashboard = () => {
    const [users, setUsers] = useState([]);
    const [items, setItems] = useState([]);
    const [loading, setLoading] = useState(true);

    const [userSearch, setUserSearch] = useState('');
    const [itemSearch, setItemSearch] = useState('');

    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [deleteType, setDeleteType] = useState(null);
    const [deleteId, setDeleteId] = useState(null);
    const [deleting, setDeleting] = useState(false);

    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    useEffect(() => {
        fetchData();
    }, []);

    const filteredUsers = users.filter((user) =>
        `${user.name} ${user.surname} ${user.email} ${user.phone || ''}`
            .toLowerCase()
            .includes(userSearch.toLowerCase())
    );

    const filteredItems = items.filter((item) =>
        `${item.title} ${item.ownerEmail} ${item.cityName || ''}`
            .toLowerCase()
            .includes(itemSearch.toLowerCase())
    );

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

            setTimeout(() => setSuccess(''), 3000);
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
            <div className="min-h-screen flex justify-center items-center bg-gradient-to-br from-slate-50 via-white to-indigo-50 dark:from-slate-950 dark:via-slate-900 dark:to-indigo-950">
                <Loader2 size={52} className="animate-spin text-primary-600" />
            </div>
        );
    }

    return (
        <div className="min-h-screen relative overflow-hidden px-4 py-12 bg-gradient-to-br from-slate-50 via-white to-indigo-50 dark:from-slate-950 dark:via-slate-900 dark:to-indigo-950">
            <div className="absolute top-[-10%] left-[-10%] w-[35%] h-[35%] bg-primary-500/10 blur-[120px] rounded-full" />
            <div className="absolute bottom-[-10%] right-[-10%] w-[35%] h-[35%] bg-indigo-500/10 blur-[120px] rounded-full" />

            <div className="max-w-7xl mx-auto relative">
                <div className="mb-10 p-8 rounded-[2.5rem] bg-white/80 dark:bg-slate-900/80 backdrop-blur-xl border border-slate-200 dark:border-slate-800 shadow-2xl">
                    <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-6">
                        <div className="flex items-center">
                            <div className="w-18 h-18 p-5 rounded-3xl bg-gradient-to-br from-primary-600 to-indigo-600 text-white shadow-xl shadow-primary-500/30 mr-5">
                                <Crown size={38} />
                            </div>

                            <div>
                                <h1 className="text-4xl md:text-5xl font-extrabold text-slate-900 dark:text-white">
                                    Admin Panel
                                </h1>

                                <p className="text-slate-500 dark:text-slate-400 mt-2">
                                    Qaytar.az sistemində istifadəçiləri və elanları idarə edin
                                </p>
                            </div>
                        </div>

                        <div className="inline-flex items-center px-5 py-3 rounded-2xl bg-primary-50 dark:bg-primary-900/20 text-primary-600 dark:text-primary-400 font-bold">
                            <ShieldCheck size={20} className="mr-2" />
                            RBAC Aktivdir
                        </div>
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
                    <div className="p-7 rounded-[2rem] bg-white/80 dark:bg-slate-900/80 backdrop-blur-xl border border-slate-200 dark:border-slate-800 shadow-xl hover:shadow-2xl transition-all duration-300 hover:-translate-y-2">
                        <div className="flex items-center justify-between">
                            <div>
                                <p className="text-slate-500 dark:text-slate-400 font-bold">
                                    Ümumi istifadəçi
                                </p>

                                <h2 className="text-5xl font-extrabold text-slate-900 dark:text-white mt-3">
                                    {users.length}
                                </h2>
                            </div>

                            <div className="w-16 h-16 rounded-3xl bg-primary-50 dark:bg-primary-900/20 text-primary-600 flex items-center justify-center">
                                <Users size={36} />
                            </div>
                        </div>
                    </div>

                    <div className="p-7 rounded-[2rem] bg-white/80 dark:bg-slate-900/80 backdrop-blur-xl border border-slate-200 dark:border-slate-800 shadow-xl hover:shadow-2xl transition-all duration-300 hover:-translate-y-2">
                        <div className="flex items-center justify-between">
                            <div>
                                <p className="text-slate-500 dark:text-slate-400 font-bold">
                                    Ümumi elan
                                </p>

                                <h2 className="text-5xl font-extrabold text-slate-900 dark:text-white mt-3">
                                    {items.length}
                                </h2>
                            </div>

                            <div className="w-16 h-16 rounded-3xl bg-indigo-50 dark:bg-indigo-900/20 text-indigo-600 flex items-center justify-center">
                                <Package size={36} />
                            </div>
                        </div>
                    </div>
                </div>

                <div className="grid lg:grid-cols-2 gap-8">
                    <div className="rounded-[2.5rem] bg-white/80 dark:bg-slate-900/80 backdrop-blur-xl border border-slate-200 dark:border-slate-800 shadow-2xl p-7">
                        <div className="flex items-center mb-6">
                            <Users size={28} className="text-primary-600 mr-3" />
                            <h2 className="text-2xl font-extrabold text-slate-900 dark:text-white">
                                İstifadəçilər
                            </h2>
                        </div>

                        <div className="relative mb-6">
                            <Search
                                size={18}
                                className="absolute left-4 top-4 text-slate-400"
                            />

                            <input
                                type="text"
                                value={userSearch}
                                onChange={(e) => setUserSearch(e.target.value)}
                                placeholder="Ad, soyad, email və ya telefon üzrə axtar..."
                                className="w-full pl-12 pr-4 py-4 rounded-2xl bg-slate-50 dark:bg-slate-800 border border-slate-200 dark:border-slate-700 focus:ring-2 focus:ring-primary-500 outline-none text-slate-900 dark:text-white"
                            />
                        </div>

                        <div className="space-y-4 max-h-[520px] overflow-y-auto pr-2">
                            {filteredUsers.length > 0 ? (
                                filteredUsers.map((user) => (
                                    <div
                                        key={user.id}
                                        className="p-5 rounded-3xl bg-slate-50 dark:bg-slate-800/70 border border-slate-200 dark:border-slate-700 hover:shadow-lg transition-all"
                                    >
                                        <div className="flex justify-between gap-4">
                                            <div>
                                                <h3 className="font-extrabold text-slate-900 dark:text-white">
                                                    {user.name} {user.surname}
                                                </h3>

                                                <p className="flex items-center text-sm text-slate-500 dark:text-slate-400 mt-2">
                                                    <Mail size={15} className="mr-2" />
                                                    {user.email}
                                                </p>

                                                <p className="flex items-center text-sm text-slate-500 dark:text-slate-400 mt-1">
                                                    <Phone size={15} className="mr-2" />
                                                    {user.phone}
                                                </p>
                                            </div>

                                            <button
                                                onClick={() => openDeleteModal('user', user.id)}
                                                className="h-11 w-11 flex items-center justify-center rounded-2xl bg-rose-50 dark:bg-rose-900/20 text-rose-600 hover:bg-rose-600 hover:text-white transition-all"
                                            >
                                                <Trash2 size={19} />
                                            </button>
                                        </div>
                                    </div>
                                ))
                            ) : (
                                <p className="text-slate-500 dark:text-slate-400">
                                    Axtarışa uyğun istifadəçi tapılmadı.
                                </p>
                            )}
                        </div>
                    </div>

                    <div className="rounded-[2.5rem] bg-white/80 dark:bg-slate-900/80 backdrop-blur-xl border border-slate-200 dark:border-slate-800 shadow-2xl p-7">
                        <div className="flex items-center mb-6">
                            <Package size={28} className="text-indigo-600 mr-3" />
                            <h2 className="text-2xl font-extrabold text-slate-900 dark:text-white">
                                Elanlar
                            </h2>
                        </div>

                        <div className="relative mb-6">
                            <Search
                                size={18}
                                className="absolute left-4 top-4 text-slate-400"
                            />

                            <input
                                type="text"
                                value={itemSearch}
                                onChange={(e) => setItemSearch(e.target.value)}
                                placeholder="Elan adı, email və ya şəhər üzrə axtar..."
                                className="w-full pl-12 pr-4 py-4 rounded-2xl bg-slate-50 dark:bg-slate-800 border border-slate-200 dark:border-slate-700 focus:ring-2 focus:ring-primary-500 outline-none text-slate-900 dark:text-white"
                            />
                        </div>

                        <div className="space-y-4 max-h-[520px] overflow-y-auto pr-2">
                            {filteredItems.length > 0 ? (
                                filteredItems.map((item) => (
                                    <div
                                        key={item.id}
                                        className="p-5 rounded-3xl bg-slate-50 dark:bg-slate-800/70 border border-slate-200 dark:border-slate-700 hover:shadow-lg transition-all"
                                    >
                                        <div className="flex justify-between gap-4">
                                            <div>
                                                <h3 className="font-extrabold text-slate-900 dark:text-white">
                                                    {item.title}
                                                </h3>

                                                <p className="flex items-center text-sm text-slate-500 dark:text-slate-400 mt-2">
                                                    <Mail size={15} className="mr-2" />
                                                    {item.ownerEmail}
                                                </p>

                                                <p className="flex items-center text-sm text-slate-500 dark:text-slate-400 mt-1">
                                                    <MapPin size={15} className="mr-2" />
                                                    {item.cityName}
                                                </p>
                                            </div>

                                            <button
                                                onClick={() => openDeleteModal('item', item.id)}
                                                className="h-11 w-11 flex items-center justify-center rounded-2xl bg-rose-50 dark:bg-rose-900/20 text-rose-600 hover:bg-rose-600 hover:text-white transition-all"
                                            >
                                                <Trash2 size={19} />
                                            </button>
                                        </div>
                                    </div>
                                ))
                            ) : (
                                <p className="text-slate-500 dark:text-slate-400">
                                    Axtarışa uyğun elan tapılmadı.
                                </p>
                            )}
                        </div>
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