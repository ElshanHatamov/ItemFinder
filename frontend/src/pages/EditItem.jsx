import React, { useEffect, useState } from 'react';
import { useNavigate, useParams, Link } from 'react-router-dom';
import api from '../api/axios';
import { ArrowLeft, Loader2, Save, MapPin, Tag, Info } from 'lucide-react';

const EditItem = () => {
    const { id } = useParams();
    const navigate = useNavigate();

    const [cities, setCities] = useState([]);
    const [form, setForm] = useState({
        title: '',
        description: '',
        itemStatus: 'LOST',
        itemType: 'OTHER',
        cityId: '',
    });

    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState('');

    useEffect(() => {
        fetchInitialData();
    }, [id]);

    const fetchInitialData = async () => {
        setLoading(true);
        setError('');

        try {
            const [itemResponse, cityResponse] = await Promise.all([
                api.get(`/api/items/${id}`),
                api.get('/api/cities'),
            ]);

            const item = itemResponse.data;
            const cityList = cityResponse.data;

            setCities(cityList);

            const selectedCity = cityList.find((city) => city.name === item.cityName);

            setForm({
                title: item.title || '',
                description: item.description || '',
                itemStatus: item.itemStatus || 'LOST',
                itemType: item.itemType || 'OTHER',
                cityId: selectedCity ? String(selectedCity.id) : '',
            });
        } catch (err) {
            console.error('Redaktə məlumatları yüklənərkən xəta:', err);
            setError('Elan məlumatları yüklənərkən xəta baş verdi.');
        } finally {
            setLoading(false);
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;

        setForm((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!form.title.trim()) {
            setError('Başlıq boş ola bilməz.');
            return;
        }

        if (!form.description.trim()) {
            setError('Təsvir boş ola bilməz.');
            return;
        }

        if (form.description.trim().length < 10) {
            setError('Təsvir minimum 10 simvol olmalıdır.');
            return;
        }

        if (!form.cityId) {
            setError('Şəhər seçilməlidir.');
            return;
        }

        setSaving(true);
        setError('');

        try {
            await api.put(`/api/items/${id}`, {
                title: form.title,
                description: form.description,
                itemStatus: form.itemStatus,
                itemType: form.itemType,
                cityId: Number(form.cityId),
            });

            navigate('/my-items');
        } catch (err) {
            console.error('Elan redaktə olunarkən xəta:', err);
            setError(
                err.response?.data?.message ||
                'Elan redaktə olunarkən xəta baş verdi.'
            );
        } finally {
            setSaving(false);
        }
    };

    if (loading) {
        return (
            <div className="min-h-screen pt-20 flex justify-center">
                <Loader2 size={48} className="animate-spin text-primary-600" />
            </div>
        );
    }

    return (
        <div className="min-h-screen pt-12 pb-20 px-4">
            <div className="max-w-3xl mx-auto">
                <Link
                    to="/my-items"
                    className="inline-flex items-center text-slate-500 hover:text-primary-600 mb-8 transition-colors"
                >
                    <ArrowLeft size={20} className="mr-2" />
                    Elanlarıma qayıt
                </Link>

                <div className="mb-10 text-center">
                    <h1 className="text-4xl font-bold text-slate-900 dark:text-white mb-4">
                        Qaytar.az elanını redaktə et
                    </h1>

                    <p className="text-slate-500 dark:text-slate-400">
                        Elanın məlumatlarını yeniləyin və dəyişiklikləri yadda saxlayın
                    </p>
                </div>

                <div className="mb-8 bg-blue-50 dark:bg-blue-900/20 border border-blue-200 dark:border-blue-800 rounded-2xl p-4 text-sm text-blue-700 dark:text-blue-300">
                    Dəyişiklikləri yadda saxladıqdan sonra elan məlumatları yenilənəcək.
                    Qaytar.az platforması yenilənmiş məlumatları dərhal istifadəçilərə göstərəcək.
                </div>

                <div className="bg-white dark:bg-slate-900 rounded-[2.5rem] shadow-2xl border border-slate-200 dark:border-slate-800 overflow-hidden">
                    <form onSubmit={handleSubmit} className="p-8 md:p-12 space-y-8">
                        {error && (
                            <div className="p-4 bg-rose-50 dark:bg-rose-900/20 border border-rose-200 dark:border-rose-800 rounded-2xl text-rose-600 dark:text-rose-400 text-sm font-medium">
                                {error}
                            </div>
                        )}

                        <div>
                            <label className="block text-sm font-bold text-slate-700 dark:text-slate-300 mb-3 flex items-center">
                                <Info size={16} className="mr-2 text-primary-500" />
                                Əşyanın başlığı
                            </label>

                            <input
                                name="title"
                                value={form.title}
                                onChange={handleChange}
                                placeholder="Məsələn: Qara pulqabı"
                                className="w-full px-6 py-4 bg-slate-50 dark:bg-slate-800 border-transparent rounded-2xl focus:ring-2 focus:ring-primary-500 outline-none text-slate-900 dark:text-white"
                            />
                        </div>

                        <div>
                            <label className="block text-sm font-bold text-slate-700 dark:text-slate-300 mb-3 flex items-center">
                                <Info size={16} className="mr-2 text-primary-500" />
                                Əşya haqqında məlumat
                            </label>

                            <textarea
                                name="description"
                                value={form.description}
                                onChange={handleChange}
                                rows={5}
                                placeholder="Əşyanın rəngi, markası, harada itdiyi və digər vacib məlumatları yazın..."
                                className="w-full px-6 py-4 bg-slate-50 dark:bg-slate-800 border-transparent rounded-2xl focus:ring-2 focus:ring-primary-500 outline-none text-slate-900 dark:text-white resize-none"
                            />
                        </div>

                        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                            <div>
                                <label className="block text-sm font-bold text-slate-700 dark:text-slate-300 mb-3 flex items-center">
                                    <Tag size={16} className="mr-2 text-primary-500" />
                                    Elanın statusu
                                </label>

                                <select
                                    name="itemStatus"
                                    value={form.itemStatus}
                                    onChange={handleChange}
                                    className="w-full px-6 py-4 bg-slate-50 dark:bg-slate-800 rounded-2xl focus:ring-2 focus:ring-primary-500 text-slate-900 dark:text-white"
                                >
                                    <option value="LOST">İtmiş</option>
                                    <option value="FOUND">Tapılmış</option>
                                </select>
                            </div>

                            <div>
                                <label className="block text-sm font-bold text-slate-700 dark:text-slate-300 mb-3 flex items-center">
                                    <Tag size={16} className="mr-2 text-primary-500" />
                                    Kateqoriya
                                </label>

                                <select
                                    name="itemType"
                                    value={form.itemType}
                                    onChange={handleChange}
                                    className="w-full px-6 py-4 bg-slate-50 dark:bg-slate-800 rounded-2xl focus:ring-2 focus:ring-primary-500 text-slate-900 dark:text-white"
                                >
                                    <option value="PHONE">Telefon</option>
                                    <option value="DOCUMENT">Sənəd</option>
                                    <option value="KEY">Açar</option>
                                    <option value="BAG">Çanta</option>
                                    <option value="WALLET">Pulqabı</option>
                                    <option value="OTHER">Digər</option>
                                </select>
                            </div>
                        </div>

                        <div>
                            <label className="block text-sm font-bold text-slate-700 dark:text-slate-300 mb-3 flex items-center">
                                <MapPin size={16} className="mr-2 text-primary-500" />
                                Şəhər
                            </label>

                            <select
                                name="cityId"
                                value={form.cityId}
                                onChange={handleChange}
                                className="w-full px-6 py-4 bg-slate-50 dark:bg-slate-800 rounded-2xl focus:ring-2 focus:ring-primary-500 text-slate-900 dark:text-white"
                            >
                                <option value="">Şəhər seçin</option>

                                {cities.map((city) => (
                                    <option key={city.id} value={city.id}>
                                        {city.name}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <button
                            type="submit"
                            disabled={saving}
                            className="w-full py-5 bg-primary-600 hover:bg-primary-700 disabled:bg-primary-400 text-white rounded-2xl font-bold transition-all shadow-xl shadow-primary-500/25 flex items-center justify-center text-lg"
                        >
                            {saving ? (
                                <Loader2 size={24} className="animate-spin" />
                            ) : (
                                <>
                                    <Save size={22} className="mr-2" />
                                    Dəyişiklikləri yadda saxla
                                </>
                            )}
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default EditItem;