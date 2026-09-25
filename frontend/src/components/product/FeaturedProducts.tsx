import ProductCard from "./ProductCard";

function FeaturedProducts() {
    return (
        <section className="bg-background">
            <div className="mx-auto max-w-7xl px-6 py-20">

                {/* Section heading */}
                <div className="mb-10">
            <span className="text-sm font-semibold uppercase tracking-wider text-primary-600">
                Handpicked for you
            </span>

                    <h2 className="mt-2 text-3xl font-bold tracking-tight text-text-primary sm:text-4xl">
                        Featured Products
                    </h2>

                    <p className="mt-3 text-text-secondary">
                        Discover some of our most popular products.
                    </p>
                </div>

                {/* Product grid */}
                <div className="grid min-w-0 gap-6 sm:grid-cols-2 lg:grid-cols-4">
                    {/* Product cards go here */}
                    <ProductCard
                        name="Wireless Headphones"
                        category="Electronics"
                        price={129.99}
                        rating={4.8}
                        reviewCount={124}
                        image="/images/headphone.jpg"
                    />

                    <ProductCard
                        name="Classic Cotton T-Shirt"
                        category="Fashion"
                        price={29.99}
                        rating={4.6}
                        reviewCount={89}
                        image="/images/tshirt.jpg"
                    />

                    <ProductCard
                        name="Modern Table Lamp"
                        category="Home & Living"
                        price={79.99}
                        rating={4.7}
                        reviewCount={56}
                        image="/images/lamp.jpg"
                    />

                    <ProductCard
                        name="Running Shoes"
                        category="Sports"
                        price={99.99}
                        rating={4.9}
                        reviewCount={203}
                        image="/images/shoes.jpg"
                    />
                </div>

            </div>
        </section>
    );
}

export default FeaturedProducts;