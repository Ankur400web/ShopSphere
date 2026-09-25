interface CategoryCardProps {
    name: string;
    description: string;
    icon: React.ReactNode;
}

function CategoryCard({name, description, icon}: CategoryCardProps) {
    return (
        <div>
            <div className="group rounded-2xl border border-border bg-surface p-6 transition-all hover:-translate-y-1 hover:shadow-md">
                <div className="mb-4 flex h-12 w-12 items-center justify-center rounded-xl bg-primary-50 text-primary-600">
                    {icon}
                </div>

                <h3 className="text-lg font-semibold text-text-primary">
                    {name}
                </h3>

                <p className="mt-2 text-sm text-text-secondary">
                    {description}
                </p>
            </div>

        </div>
    );
}

export default CategoryCard;