import {
    Laptop,
    Shirt,
    Home,
    Dumbbell
} from "lucide-react";

import CategoryCard from "./CategoryCard";



function CategorySection() {
    return (
        <section className="bg-surface">
            <div className="mx-auto max-w-7xl px-6 py-20">

                {/* Section heading */}
                <div className="mb-10">
                    <span className="text-sm font-semibold uppercase tracking-wider text-primary-600">
                        Explore
                    </span>

                    <h2 className="mt-2 text-3xl font-bold tracking-tight text-text-primary sm:text-4xl">
                        Shop by category
                    </h2>

                    <p className="mt-3 max-w-2xl text-text-secondary">
                        Find what you're looking for across our most popular categories.
                    </p>
                </div>


                <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-4">

                    <CategoryCard
                        name="Electronics"
                        description="Devices & gadgets"
                        icon={<Laptop size={24} />}
                    />

                    <CategoryCard
                        name="Fashion"
                        description="Style & apparel"
                        icon={<Shirt size={24} />}
                    />

                    <CategoryCard
                        name="Home & Living"
                        description="Make your space yours"
                        icon={<Home size={24} />}
                    />

                    <CategoryCard
                        name="Sports"
                        description="Gear for every activity"
                        icon={<Dumbbell size={24} />}
                    />

                </div>

            </div>
        </section>
    );
}

export default CategorySection;
