interface ProductCardProps {
    name: string;
    category: string;
    price: number;
    rating: number;
    reviewCount: number;
    image: string;
}

function ProductCard({
     name,
     category,
     price,
     rating,
     reviewCount,
     image
 }: ProductCardProps) {
    return (
        <div>
            <div className="group overflow-hidden rounded-2xl border border-border bg-surface transition-all hover:-translate-y-1 hover:shadow-md">
                <div className="aspect-square overflow-hidden bg-background">
                    <img
                        src={image}
                        alt={name}
                        className="h-full w-full object-cover transition-transform duration-300 group-hover:scale-105"
                    />
                    <div className="p-5">
                        <p className="text-xs font-medium uppercase tracking-wide text-primary-600">
                            {category}
                        </p>

                        <h3 className="mt-2 text-lg font-semibold text-text-primary">
                            {name}
                        </h3>
                        <div className="mt-3 flex items-center gap-2">
                            <span className="text-sm font-medium text-text-primary">
                                ★ {rating}
                            </span>
                            <p className="mt-4 text-xl font-bold text-text-primary">
                                ${price.toFixed(2)}
                            </p>

                            <span className="text-sm text-text-muted">
                                ({reviewCount} reviews)
                            </span>
                        </div>
                        <button
                            type="button"
                            className="mt-4 w-full rounded-lg bg-primary-600 px-4 py-3 font-semibold text-white transition-colors hover:bg-primary-700"
                        >
                            Add to Cart
                        </button>
                    </div>
                </div>
            </div>

        </div>
    );
}

export default ProductCard;